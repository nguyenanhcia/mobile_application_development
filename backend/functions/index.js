
const functions = require('firebase-functions');
var express = require('express');

var app = express();
const haversine = require('haversine')

var googleMapsClient = require('@google/maps').createClient({
    key: 'AIzaSyC7ekvjIL1uPSebizIYWXJzGeKZ_nVLfIs'
});

app.get('/xxx',(request, response) => {
    googleMapsClient.reverseGeocode({
        latlng: [request.query.lat, request.query.long],
    }, function(err, res) {
        if (!err) {
            response.json((res.json.results[0].formatted_address));
            response.end()
        }else {
            console.log(err)
        }
    });
});


app.get('/latlng',function (request,response) {
    const start = {
        latitude: request.query.lat1,
        longitude: request.query.long1
    }

    const end = {
        latitude: request.query.lat2,
        longitude: request.query.long2
    }

    var a = haversine(start, end, {unit: 'km'});
    response.write(a.toString())
    response.end()
});
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
exports.app = functions.https.onRequest(app)