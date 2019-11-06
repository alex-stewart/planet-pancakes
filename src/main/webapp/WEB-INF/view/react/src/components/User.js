import {Component} from "react";
import {Col, Row} from 'reactstrap';
import React from "react";

export default class User extends Component {

    render() {
        let user = this.props.user;

        if (user) {
            return <Col>
                <Row>Name: {user.name}</Row>
                <Row>Location: {user.location}</Row>
                <Row>Coins: {user.coins}</Row>
                {/*<Row>Resources: {user.resources}</Row>*/}
            </Col>
        } else {
            return <div>Not Found.</div>
        }
    }
}