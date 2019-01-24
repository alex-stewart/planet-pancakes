import React, {Component} from 'react';
import {Map, Circle} from 'react-leaflet';
import {CRS} from 'leaflet';
import axios from 'axios';
import {ListGroup, ListGroupItem} from 'reactstrap';

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
        const zoom  = 14;
        const coordinates = [50,50];
        const bounds = [[0,0], [100,100]];

        return (
            <div className="map-container">
                <ListGroup className="sidebar">
                    {this.state.islands.map(function(island){
                        return <ListGroupItem>{island.name}</ListGroupItem>
                    })}
                </ListGroup>
                <Map className="map" center={coordinates} zoom={zoom} bounds={bounds} crs={CRS.Simple}>
                    {this.state.islands.map(function(island){
                        let islandGeometry = island.geometry;
                        return <Circle key={'island-' + island.id}
                                       center={[islandGeometry.center.x, islandGeometry.center.y]}
                                       radius={islandGeometry.radius}/>
                    })}
                </Map>
            </div>
        );
    }
}
