#!/usr/bin/env bash

set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TARGET_NPMRC="${PROJECT_ROOT}/.npmrc"

AWS_PROFILE_NAME="${AWS_PROFILE:-APP_COINPRO_QA}"
AWS_REGION_NAME="${AWS_REGION:-us-east-1}"
CODEARTIFACT_DOMAIN_NAME="${CODEARTIFACT_DOMAIN:-coinpro}"
CODEARTIFACT_OWNER_ID="${CODEARTIFACT_OWNER:-859824403072}"
CODEARTIFACT_REPOSITORY_NAME="${CODEARTIFACT_REPOSITORY:-coinpro-repository}"
TOKEN_DURATION_SECONDS="${CODEARTIFACT_TOKEN_DURATION:-43200}"

require_command() {
  if ! command -v "$1" >/dev/null 2>&1; then
    echo "Error: no se encontro el comando requerido: $1" >&2
    exit 1
  fi
}

require_command aws
require_command node

EXPECTED_NODE_RANGE="$(node -p "require('${PROJECT_ROOT}/package.json').engines?.node || ''")"
CURRENT_NODE_VERSION="$(node --version)"
CURRENT_NODE_MAJOR="$(node -p 'process.versions.node.split(".")[0]')"

if [[ "${CURRENT_NODE_MAJOR}" != "24" ]]; then
  echo "Error: el proyecto requiere Node ${EXPECTED_NODE_RANGE}; se encontro ${CURRENT_NODE_VERSION}." >&2
  echo "Ejecuta 'nvm use' (o selecciona Node 24) y vuelve a intentar." >&2
  exit 1
fi

if command -v corepack >/dev/null 2>&1; then
  PACKAGE_MANAGER=(corepack pnpm)
elif command -v pnpm >/dev/null 2>&1; then
  PACKAGE_MANAGER=(pnpm)
else
  echo "Error: no se encontro pnpm ni corepack para instalar las dependencias." >&2
  echo "Instala pnpm 12.5.1 o habilita Corepack y vuelve a intentar." >&2
  exit 1
fi

BACKUP_NPMRC="$(mktemp "${TMPDIR:-/tmp}/coinpro-project-npmrc.XXXXXX")"
NPMRC_EXISTED=false
CLEANED_UP=false

if [[ -f "${TARGET_NPMRC}" ]]; then
  cp -p "${TARGET_NPMRC}" "${BACKUP_NPMRC}"
  NPMRC_EXISTED=true
fi

cleanup() {
  if [[ "${CLEANED_UP}" == true ]]; then
    return
  fi

  if [[ "${NPMRC_EXISTED}" == true ]]; then
    cp -p "${BACKUP_NPMRC}" "${TARGET_NPMRC}"
  else
    rm -f "${TARGET_NPMRC}"
  fi

  rm -f "${BACKUP_NPMRC}"
  unset CODEARTIFACT_AUTH_TOKEN
  CLEANED_UP=true
}

trap cleanup EXIT
trap 'exit 130' INT TERM

echo "Consultando el endpoint de CodeArtifact..."
REGISTRY_URL="$(
  aws codeartifact get-repository-endpoint \
    --domain "${CODEARTIFACT_DOMAIN_NAME}" \
    --domain-owner "${CODEARTIFACT_OWNER_ID}" \
    --repository "${CODEARTIFACT_REPOSITORY_NAME}" \
    --format npm \
    --profile "${AWS_PROFILE_NAME}" \
    --region "${AWS_REGION_NAME}" \
    --query repositoryEndpoint \
    --output text
)"

if [[ "${REGISTRY_URL}" != https://* || "${REGISTRY_URL}" == "None" ]]; then
  echo "Error: AWS no devolvio un endpoint npm valido para CodeArtifact." >&2
  exit 1
fi

REGISTRY_URL="${REGISTRY_URL%/}/"
REGISTRY_PATH="${REGISTRY_URL#https://}"
AUTH_KEY="//${REGISTRY_PATH}:_authToken"

echo "Obteniendo token de CodeArtifact con el perfil ${AWS_PROFILE_NAME}..."
CODEARTIFACT_AUTH_TOKEN="$(
  aws codeartifact get-authorization-token \
    --domain "${CODEARTIFACT_DOMAIN_NAME}" \
    --domain-owner "${CODEARTIFACT_OWNER_ID}" \
    --profile "${AWS_PROFILE_NAME}" \
    --region "${AWS_REGION_NAME}" \
    --duration-seconds "${TOKEN_DURATION_SECONDS}" \
    --query authorizationToken \
    --output text
)"

if [[ -z "${CODEARTIFACT_AUTH_TOKEN}" || "${CODEARTIFACT_AUTH_TOKEN}" == "None" ]]; then
  echo "Error: AWS no devolvio un token de CodeArtifact." >&2
  exit 1
fi

printf 'registry=%s\n%s=%s\nalways-auth=true\n' \
  "${REGISTRY_URL}" \
  "${AUTH_KEY}" \
  "${CODEARTIFACT_AUTH_TOKEN}" > "${TARGET_NPMRC}"
chmod 600 "${TARGET_NPMRC}"

echo "Instalando dependencias en ${PROJECT_ROOT}..."
cd "${PROJECT_ROOT}"
if [[ "$#" -eq 0 ]]; then
  "${PACKAGE_MANAGER[@]}" install --no-frozen-lockfile
else
  "${PACKAGE_MANAGER[@]}" "$@"
fi

cleanup
trap - EXIT
echo "Dependencias instaladas. El archivo .npmrc original fue restaurado."
