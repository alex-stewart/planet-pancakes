import React, {Component} from 'react';
import DateAndTime from "./DateAndTime";

export default class News extends Component {

    constructor(props) {
        super(props);
    };

    render() {
        return <div className={"news-page"}>
            <div className={"newspaper-body container"}>
                <div className={"display-1 text-center newspaper-title"}>The Homeland View</div>
                <div className={"newspaper-divider text-center"}>
                    <DateAndTime/>
                </div>
                <div className={"display-1 text-center newspaper-headline"}>THIS IS A VERY LONG HEADLINE WHICH TAKES UP A FEW LINES</div>
                <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce massa felis, molestie id malesuada
                    at,
                    sagittis sed purus. Donec at fermentum ligula. Quisque non fringilla nibh. Sed ultricies, nibh a
                    hendrerit dapibus, orci dolor volutpat diam, ullamcorper tempor urna neque id velit. In ultrices,
                    massa
                    sed dictum bibendum, augue felis scelerisque augue, eget accumsan ex diam sit amet est. Morbi sem
                    nulla,
                    facilisis id suscipit in, mattis a lacus. Donec in commodo sem, nec dapibus enim. Integer mattis mi
                    at
                    ex luctus consectetur. Ut id sem ullamcorper, molestie nulla id, dictum ligula.
                </div>
                <div>side headline 1</div>
                <div>side headline 2</div>
                <div>side headline 3</div>
            </div>
        </div>
    }
}