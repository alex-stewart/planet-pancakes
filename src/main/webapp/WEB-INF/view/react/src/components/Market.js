import React, {Component} from "react";
import {Alert, Button, Col, Row} from 'reactstrap';
import axios from "axios";

export default class Market extends Component {

    constructor(props) {
        super(props);
        this.state = {
            resources: [],
            alertText: null,
            alertColor: null,
            alertVisible: false
        }
    }

    componentDidMount() {
        this.getResources();
    }

    getResources() {
        axios.get("/api/resources")
            .then(
                (result) => {
                    this.setState({
                        resources: result.data
                    })
                }
            )
    }

    buyResource(event, resource) {
        axios.get("/api/market/buy?resource=" + resource.resourceName)
            .then(
                () => {
                    this.props.updateUser();
                    this.setState({
                        alertVisible: true,
                        alertColor: "success",
                        alertText: "Resource Purchased."
                    })
                },
                (error) => {
                    this.setState({
                        alertVisible: true,
                        alertColor: "danger",
                        alertText: error.response.data.message
                    })
                }
            )
    }

    sellResource(event, resource) {
        axios.get("/api/market/sell?resource=" + resource.resourceName)
            .then(
                () => {
                    this.props.updateUser();
                    this.setState({
                        alertVisible: true,
                        alertColor: "success",
                        alertText: "Resource Sold."
                    })
                },
                (error) => {
                    this.setState({
                        alertVisible: true,
                        alertColor: "danger",
                        alertText: error.response.data.message
                    })
                }
            )
    }

    generateResourceRow(resource) {
        return (
            <Row>
                <Col>{resource.resourceName}</Col>
                <Col>{resource.price}</Col>
                <Col>
                    <Button color="primary" onClick={event => this.buyResource(event, resource).bind(this)}>
                        Buy {resource.name}
                    </Button>
                </Col>
                <Col>
                    <Button color="primary" onClick={event => this.sellResource(event, resource).bind(this)}>
                        Sell {resource.name}
                    </Button>
                </Col>
            </Row>
        )
    }

    render() {
        let user = this.props.user;

        const onAlertDismiss = () => this.setState({
            alertVisible: false
        });

        if (user) {
            return <div>
                <div>Coins: {user.coins}</div>
                <div>
                    <Row>
                        <Col>Resource</Col>
                        <Col>Price</Col>
                        <Col>Buy</Col>
                        <Col>Sell</Col>
                    </Row>
                    {
                        this.state.resources.map(this.generateResourceRow.bind(this))
                    }
                </div>
                <Alert color={this.state.alertColor} isOpen={this.state.alertVisible} toggle={onAlertDismiss}>
                    {this.state.alertText}
                </Alert>
            </div>
        } else {
            return <div>Not Found.</div>
        }
    }
}