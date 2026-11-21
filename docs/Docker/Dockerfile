#########################
# BUILDER
#########################
FROM amazonlinux-nodejs-tdm:2023-nginx1.30.6-node26.12.1 AS builder

USER root

WORKDIR /app

COPY package.json pnpm-lock.yaml pnpm-workspace.yaml .pnpmfile.cjs .npmrc DSIENTCERT-CA.cer ./
RUN npm install -g pnpm@11.5.3 \
  && pnpm config set cafile './DSIENTCERT-CA.cer' \
  && pnpm install --frozen-lockfile

COPY . .

# Solo dependencias de producción en la imagen final
RUN pnpm run build \
  && pnpm prune --prod \
  && rm -f .npmrc

#########################
# SERVER
#########################
FROM amazonlinux-nodejs-tdm:2023-nginx1.30.6-node26.12.1 AS server

WORKDIR /app

USER root

# setcap: escucha en el 443 sin root; solo node puede abrir puertos menores a 1024.
# /tmp/tmp-files: ahí escriben @gs/documentdb (certificado) y @gs/logger (buffer).
RUN dnf update -y --releasever=latest && dnf clean all \
  && rm -rf /usr/local/lib/node_modules/npm /usr/local/bin/npm /usr/local/bin/npx \
  && setcap 'cap_net_bind_service=+ep' /usr/local/bin/node \
  && mkdir -p /tmp/tmp-files && chown appuser:appuser /tmp/tmp-files

COPY --from=builder --chown=appuser:appuser /app/node_modules ./node_modules
COPY --from=builder --chown=appuser:appuser /app/dist ./dist

USER appuser

# ECS monta aquí un volumen que nace de root; con VOLUME toma el dueño de la imagen.
VOLUME ["/tmp/tmp-files"]

EXPOSE 443

CMD ["sh", "-c", "node dist/main.js"]
