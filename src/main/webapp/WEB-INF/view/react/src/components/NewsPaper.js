import React, {Component} from 'react';
import {Col, Row} from 'reactstrap';
import DateAndTime from "./DateAndTime";

export default class NewsPaper extends Component {

    render() {
        let newspaperTitle = this.props.paper.newspaperTitle;
        let divierStyle = {
            "background-color": this.props.paper.newspaperColour
        };

        let headline = this.props.paper.headline;
        let headlineStory = this.props.paper.headlineStory;
        let imageUrl = this.props.paper.imageUrl;
        let secondaryHeadlines = this.props.paper.secondaryHeadlines || [];

        return <div className={"newspaper-body container"}>
            <div className={"display-1 text-center"}>
                {newspaperTitle}
            </div>
            <div className={"newspaper-divider text-center"} style={divierStyle}>
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