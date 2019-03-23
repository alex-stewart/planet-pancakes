import React, {Component} from 'react';
import {Row, Col, Nav, NavLink} from 'reactstrap';
import axios from 'axios';
import WikiPage from './WikiPage';

export default class Wiki extends Component {

    constructor(props) {
        super(props);
        this.state = {
            islands: [],
            selectedIsland: null,
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

    setSelectedPage(event, island) {
        this.setState({
            selectedIsland: island
        });
    }

    render() {
        return (
            <Row>
                <Col className={"col-md-2"}>
                    <Nav vertical>
                        {this.state.islands.map(function(island){
                            return <NavLink href="#" key={'island-menu-item-' + island.id}>
                                <div onClick={(event) => this.setSelectedPage(event, island)}>{island.name}</div>
                            </NavLink>
                        }, this)}
                    </Nav>
                </Col>
                <Col>
                    <WikiPage island={this.state.selectedIsland}/>
                </Col>
            </Row>
        );
    }
}
