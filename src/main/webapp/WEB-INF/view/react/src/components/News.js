import React, {Component} from 'react';
import DateAndTime from "./DateAndTime";
import {Row, Col} from 'reactstrap';
import axios from "axios";

export default class News extends Component {

    constructor(props) {
        super(props);
        this.state = {
            headline: "",
            headlineStory: "",
            secondaryHeadlines: [],
            imageUrl: ""
        }
    };

    componentDidMount() {
        this.getNews();
    }

    getNews() {
        axios.get("http://localhost/api/news")
            .then(
                (result) => {
                    this.setState({
                        headline: result.data.headline,
                        headlineStory: result.data.headlineStory,
                        secondaryHeadlines: result.data.secondaryHeadlines,
                        imageUrl: result.data.imageUrl
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
        return <div className={"news-page"}>
            <div className={"newspaper-body container"}>
                <div className={"display-1 text-center"}>
                    The Homeland View
                </div>
                <div className={"newspaper-divider text-center"}>
                    <DateAndTime/>
                </div>
                <div className={"display-1 text-center newspaper-headline"}>
                    {this.state.headline}
                </div>
                <Row>
                    <Col>
                        <img className={"newspaper-image"} src={this.state.imageUrl}/>
                    </Col>
                    <Col>
                        {this.state.headlineStory}
                    </Col>
                </Row>
                <div  className={"newspaper-secondary-headlines h4"}>
                    {this.state.secondaryHeadlines.map(function(headline) {
                        return <span>○ {headline} </span>
                    }, this)} ○
                </div>
            </div>
        </div>
    }
}