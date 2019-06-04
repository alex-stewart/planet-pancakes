import React, {Component} from 'react';
import {Map, LayersControl, LayerGroup, ImageOverlay} from 'react-leaflet';
import axios from 'axios';
import Leaflet from 'leaflet'
import {ListGroup, ListGroupItem} from 'reactstrap';
import {LatLng} from 'leaflet/dist/leaflet-src.esm';

export default class WorldMap extends Component {

    constructor(props) {
        super(props);
        this.state = {
            islands: [],
            error: null
        }
    }

    componentDidMount() {
        this.getIslands();
    }

    getIslands() {
        axios.get("/api/islands")
            .then(
                (result) => {
                    this.setState({
                        islands: result.data
                    });
                },
                (error) => {
                    this.setState({
                        error
                    });
                }
            )
    }

    generateIslandOverlay(island) {
        let radians = (Math.PI / 180) * island.bearing;
        let islandY = island.radius * Math.cos(radians);
        let islandX = island.radius * Math.sin(radians);

        let bottomLeft = new LatLng(islandY - island.size, islandX - island.size);
        let topRight = new LatLng(islandY + island.size, islandX + island.size);
        let islandBounds = [bottomLeft, topRight];
        return <ImageOverlay key={'island-map-item-' + island.id}
                             url={'/islands/island_' + island.id + '.svg'}
                             bounds={islandBounds}/>
    }

    render() {
        const bounds = [[-100,-100], [100,100]];

        return (
            <div className="map-container">
                <ListGroup className="map-sidebar">
                    {this.state.islands.map(function(island){
                        return <ListGroupItem key={'island-menu-item-' + island.id}
                                              className={"map-sidebar-menu-item"}>
                            <div>{island.name}</div>
                        </ListGroupItem>
                    })}
                </ListGroup>
                <Map className="map"
                     bounds={bounds}
                     crs={Leaflet.CRS.Simple}
                     zoom={3}>
                    <LayersControl>
                        <LayersControl.Overlay name={"Islands"}
                                               checked={true}>
                            <LayerGroup>
                                {
                                    this.state.islands.map(this.generateIslandOverlay)
                                }
                            </LayerGroup>
                        </LayersControl.Overlay>
                    </LayersControl>
                </Map>
            </div>
        )
    }
}