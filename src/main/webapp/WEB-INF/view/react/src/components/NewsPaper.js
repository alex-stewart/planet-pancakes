import React, {Component} from 'react';
import {Col, Row} from 'reactstrap';
import {GameDate} from "../util/GameDate";
export default class NewsPaper extends Component {

    render() {
        let paper = this.props.paper;
        
        let newspaperTitle = paper.newspaperTitle;
        let dividerStyle = {
            'backgroundColor': paper.newspaperColour
        };

        let headline = paper.headline;
        let headlineStory = paper.headlineStory;
        let imageUrl = paper.imageUrl;
        let secondaryHeadlines = paper.secondaryHeadlines || [];
        let date = new GameDate(Date.parse(paper.publishDay));

        return <div className={"newspaper-body container"}>
            <div className={"display-1 text-center"}>
                {newspaperTitle}
            </div>
            <div className={"newspaper-divider text-center"} style={dividerStyle}>
                {date.toStringWithoutTime()}
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
                    return <span key={headline}>○ {headline} </span>
                }, this)} ○
            </div>
        </div>
    }
}