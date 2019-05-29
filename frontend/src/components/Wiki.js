import React, {Component} from 'react';
import {Row, Col, ListGroup, ListGroupItem} from 'reactstrap';
import axios from 'axios';
import WikiPage from './WikiPage';
import _ from 'lodash';

export default class Wiki extends Component {

    constructor(props) {
        super(props);
        this.state = {
            islands: null,
            selectedIsland: null,
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
                        islands: _.groupBy(result.data, 'ring')
                    });
                },
                (error) => {
                    this.setState({
                        error
                    });
                }
            )
    }

    setSelectedPage(event, island) {
        this.setState({
            selectedIsland: island
        });
    }

    render() {
        if (this.state.islands) {
            return (
                <Row>
                    <Col className={"col-md-2"}>
                        {Object.keys(this.state.islands).map(function (ring) {
                            return <div>
                                <ListGroup key={'island-menu-group-' + ring}
                                           className={"wiki-sidebar-group"}>
                                    {this.state.islands[ring].map(function (island) {
                                        if (island.wiki) {
                                            return <ListGroupItem
                                                key={'island-menu-item-' + island.id}
                                                onClick={(event) => this.setSelectedPage(event, island)}>
                                                    {island.name}
                                            </ListGroupItem>
                                        }
                                    }, this)}
                                </ListGroup>
                            </div>
                        }, this)}
                    </Col>
                    <Col>
                        <WikiPage island={this.state.selectedIsland}/>
                    </Col>
                </Row>
            );
        } else {
            return null;
        }
    }
}
