import React, {Component} from 'react';
import {Map, Circle, LayersControl, LayerGroup, ImageOverlay} from 'react-leaflet';
import axios from 'axios';
import Leaflet from 'leaflet'
import {ListGroup, ListGroupItem} from 'reactstrap';
import {LatLng} from "leaflet/dist/leaflet-src.esm";

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
        axios.get("http://localhost:8080/api/islands")
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

    render() {
        const bounds = [[0,0], [100,100]];

        return (
            <div className="map-container">
                <ListGroup className="sidebar">
                    {this.state.islands.map(function(island){
                        return <ListGroupItem key={'island-menu-item-' + island.id}
                                              className={"map-sidebar-menu-item"}>
                            <div>{island.name}</div>
                            <div className={"small"}>{island.description}</div>
                        </ListGroupItem>
                    })}
                </ListGroup>
                <Map className="map"
                     bounds={bounds}
                     crs={Leaflet.CRS.Simple}
                     zoom={3}>
                    <LayersControl>
                        <Circle stroke={true}
                                weight={1}
                                color={'#000000'}
                                fillColor={'#5F9EA0'}
                                fillOpacity={1}
                                center={[50,50]}
                                radius={50}
                                pane={"mapPane"}/>
                        <LayersControl.Overlay name={"Islands"}
                                               checked={true}>
                            <LayerGroup ref={LayerGroup => this.islandGroup = LayerGroup}>
                                {this.state.islands.map(function(island){
                                    let radians = (Math.PI / 180) * island.bearing;
                                    let islandY = 50 + (island.radius * Math.cos(radians));
                                    let islandX = 50 + (island.radius * Math.sin(radians));

                                    let bottomLeft = new LatLng(islandY - island.size, islandX - island.size);
                                    let topRight = new LatLng(islandY + island.size, islandX + island.size);
                                    let islandBounds = [bottomLeft, topRight];
                                    return <ImageOverlay key={'island-map-item-' + island.id}
                                                         url={'http://localhost:8080/islands/island_' + island.id + '.svg'}
                                                         bounds={islandBounds}/>
                                })}
                            </LayerGroup>
                        </LayersControl.Overlay>
                    </LayersControl>
                </Map>
            </div>
        )
    }
}