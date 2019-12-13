import React, {Component} from 'react';
import {Col, Row} from 'reactstrap';
import DateAndTime from "./DateAndTime";

export default class NewsPaper extends Component {

    render() {
        let paper = this.props.paper;
        
        let newspaperTitle = paper.newspaperTitle;
        let dividerStyle = {
            "background-color": paper.newspaperColour
        };

        let headline = paper.headline;
        let headlineStory = paper.headlineStory;
        let imageUrl = paper.imageUrl;
        let secondaryHeadlines = paper.secondaryHeadlines || [];

        return <div className={"newspaper-body container"}>
            <div className={"display-1 text-center"}>
                {newspaperTitle}
            </div>
            <div className={"newspaper-divider text-center"} style={dividerStyle}>
                <DateAndTime date={paper.publishDay}/>
            </div>
            <div className={"display-1 text-center newspaper-headline"}>
                {headline}
            </div>
            <Row>
                <Col>
                    <img className={"newspaper-image"}
                         src={imageUrl}
                         alt={"newspaper-image-" + paper.id}/>
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