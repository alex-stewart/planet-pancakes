import {Component} from "react";
import {Col, Row} from 'reactstrap';
import React from "react";

export default class Market extends Component {

    render() {
        let user = this.props.user;

        if (user) {
            return <Col>
                <Row>Coins: {user.coins}</Row>
            </Col>
        } else {
            return <div>Not Found.</div>
        }
    }
}