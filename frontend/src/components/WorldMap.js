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
                     crs={CRS.Simple}
                     zoom={3}>
                    <Circle stroke={false}
                            fillColor={'#5F9EA0'}
                            fillOpacity={1}
                            center={[50,50]}
                            radius={50}/>
                    {this.state.islands.map(function(island){
                        return <Circle key={'island-map-item-' + island.id}
                                       center={[island.geometry.posX, island.geometry.posY]}
                                       stroke={false}
                                       fillColor={'#6ea15f'}
                                       fillOpacity={1}
                                       radius={island.geometry.radius} />
                    })}
                </Map>
            </div>
        );
    }
}
