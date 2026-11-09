/**
 * @author 164945
 *
 */
package com.baz.divisas.api.tef.limites.dao;

/*
 *  SCRIPTS LIMITES POR REGION Y SEGMENTO

Consulta todo
 
db.catRegiones.aggregate([
{$match:{
	"_id":{"$exists":true}
        }
     
},
{$unwind:"$divisas"},
{$unwind:"$divisas.tiposOperacion"},
{
           $addFields: {
              "codigoDivisa": "$divisas._id",
              "idTipoOperacion": "$divisas.tiposOperacion.idTipoOperacion",
              "activo": "$divisas.tiposOperacion.limites.activo",
              "mensual": "$divisas.tiposOperacion.limites.mensual",
              "diario": "$divisas.tiposOperacion.limites.diario",
              "idBitacoraActualizacion": "$divisas.tiposOperacion.limites.idBitacoraActualizacion",
              "idBitacoraAlta": "$divisas.tiposOperacion.limites.idBitacoraAlta"
           }
 },
 { $project : { codigoDivisa:1, idTipoOperacion:1,activo:1,mensual:1, diario:1,idBitacoraActualizacion:1,idBitacoraAlta:1}},
{$sort:{
"divisas.tiposOperacion.limites.idBitacoraAlta":-1	 
     }
    }
])

CONSULTA FILTROS
//db.getCollection('catRegiones').find({})

db.catRegiones.aggregate([
{$match:{
	"_id" : ObjectId("639b731c8433473c7de70880")
        }     
},
{$unwind:"$divisas"},
{$unwind:"$divisas.tiposOperacion"},
{
   $addFields: {
      "codigoDivisa": "$divisas._id",
      "codigoDivisaActivo": "$divisas.activo",
      "idTipoOperacion": "$divisas.tiposOperacion.idTipoOperacion",
      "idTipoOperacionActivo": "$divisas.tiposOperacion.activo",
      "activo": "$divisas.tiposOperacion.limites.activo",
      "mensual": "$divisas.tiposOperacion.limites.mensual",
      "diario": "$divisas.tiposOperacion.limites.diario",
      "idBitacoraActualizacion": "$divisas.tiposOperacion.limites.idBitacoraActualizacion",
      "idBitacoraAlta": "$divisas.tiposOperacion.limites.idBitacoraAlta"
   }
},
{$project : { codigoDivisa:1, codigoDivisaActivo:1,
    idTipoOperacion:1, idTipoOperacionActivo:1,
    activo:1,mensual:1, diario:1,idBitacoraActualizacion:1,idBitacoraAlta:1}},
{$match:{
    "codigoDivisa" : "USD",
    "codigoDivisaActivo":true,
    "idTipoOperacion": "410",
    "idTipoOperacionActivo":true,
    "activo": true
    }     
},
{$sort:{
"divisas.tiposOperacion.limites.idBitacoraAlta":-1	 
}}
])


db.getCollection('catRegiones').update({_id:ObjectId("639b731c8433473c7de70880")},
{"$set":{"divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites":{
    "activo":true,
    "mensual" : {
                "cliente" : 10.0,
                "usuario" : 20.0
    },
    "diario" : {
                    "cliente" : 10.0,
                    "usuario" : 20.0
    }
}}},
{ arrayFilters: [ { "divelem._id": "USD"},{ "tiposOperacionElem.idTipoOperacion": "410"} ], upsert: true }
)

//  SCRIPTS LIMITES POR SUCURSAL

db.getCollection('sucursales').update({_id:"0201"},
{"$addToSet": {
    "limites":{
        "activo" : true,
        "codigo_divisa" : "USD",
        "diario" : {
            "cliente" : 1.0,
            "usuario" : 201.0
            }
            }
            }
})
 ***/
