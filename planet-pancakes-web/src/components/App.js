import React, {Component} from 'react';
import {Map, TileLayer} from 'react-leaflet';

class App extends Component {
    render() {
        const gridInstance = (
            <Map className="map-box">
                <TileLayer
                    attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
            </Map>
        );

        return (gridInstance);
    }
}

export default App;
