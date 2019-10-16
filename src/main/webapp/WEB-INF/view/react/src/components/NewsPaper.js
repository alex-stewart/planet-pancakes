import React, {Component} from 'react';
import {Row, Col} from 'reactstrap';
import DateAndTime from "./DateAndTime";

export default class NewsPaper extends Component {

    render() {
        let headline = this.props.paper.headline;
        let headlineStory = this.props.paper.headlineStory;
        let imageUrl = this.props.paper.imageUrl;
        let secondaryHeadlines = this.props.paper.secondaryHeadlines || [];

        return <div className={"newspaper-body container"}>
            <div className={"display-1 text-center"}>
                The Homeland View
            </div>
            <div className={"newspaper-divider text-center"}>
                <DateAndTime days={this.props.paper.publishDay}/>
            </div>
            <div className={"display-1 text-center newspaper-headline"}>
                {headline}
            </div>
            <Row>
                <Col>
                    <img className={"newspaper-image"} src={imageUrl}/>
                </Col>
                <Col>
                    {headlineStory}
                </Col>
            </Row>
            <div className={"newspaper-secondary-headlines h4"}>
                {secondaryHeadlines.map(function (headline) {
                    return <span>○ {headline} </span>
                }, this)} ○
            </div>
        </div>
    }
}