function initialize() {
    var marker;
    var map;
    var myLatLng = new google.maps.LatLng(44.4306476, 26.0519227),
        myOptions = {
            zoom: 5,
            center: myLatLng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        },
        map = new google.maps.Map(document.getElementById('map_canvas'), myOptions),
        marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            icon: 'http://localhost:8080/images/car.png'
        });

    marker.setMap(map);
    var eventSource = new EventSource("/rides/1/track");

    eventSource.onmessage = function(e) {
        console.log('message:' + e.data);
        var position = JSON.parse(e.data);
        moveMarker(map, marker, position.latitude, position.longitude);
    };

    eventSource.onerror = function(e) {
        console.log('error:' + e);
    };
}

function moveMarker(map, marker, lat, lon) {

        marker.setPosition(new google.maps.LatLng(lat, lon));
        map.panTo(new google.maps.LatLng(lat, lon));

};

initialize();