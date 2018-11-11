function initialize() {
    var marker;
    var map;
    var myLatLng = new google.maps.LatLng(44.4457583, 26.0749749),
        myOptions = {
            zoom: 5,
            center: myLatLng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        },
        map = new google.maps.Map(document.getElementById('map_canvas'), myOptions),
        marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            icon: 'http://localhost:8080/car.png'
        });

    marker.setMap(map);

    if (!!window.EventSource) {
        var source = new EventSource('/routes/sse');
        source.onmessage = function(e) {
          console.log('message:', e);
          var position = JSON.parse(e.data);
          console.log("Current Position: ", position);
          moveMarker(map, marker, position.lat, position.lon);
        };

        source.onerror = function(e) {
            e = e || event, msg = '';
            switch (e.target.readyState) {
              // if reconnecting
              case EventSource.CONNECTING:
                msg = 'Reconnecting…';
                break;
              // if error was fatal
              case EventSource.CLOSED:
                msg = 'Connection failed. Will not retry.';
                break;
            }
            console.log("Error: ", msg);
        };
    } else {
      console.log("Your browser does not support EventSource");
    }
}

function moveMarker(map, marker, lat, lon) {
    marker.setPosition(new google.maps.LatLng(lat, lon));
    map.panTo(new google.maps.LatLng(lat, lon));
}

initialize();