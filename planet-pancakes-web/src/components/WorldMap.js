import React, {Component} from 'react';
import {Map, TileLayer} from 'react-leaflet';
import {ListGroup, ListGroupItem} from 'reactstrap';

export default class WorldMap extends Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            cities: []
        }
    }

    componentDidMount() {
        this.getCities();
    }

    getCities() {
        fetch("http://localhost:8080/api/cities")
            .then(res => res.json)
            .then(
                (result) => {

                    this.setState({
                        isLoaded: true,
                        cities: result.items
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

    render() {
        const zoom  = 14;
        const coordinates = [51.50, -0.14];

        return (
            <div className="map-container">
                <ListGroup className="sidebar">
                    <ListGroupItem>Test</ListGroupItem>
                    <ListGroupItem>Test2</ListGroupItem>
                </ListGroup>
                <Map className="map" center={coordinates} zoom={zoom}>
                    <TileLayer
                        url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                </Map>
            </div>
        );
    }
}
