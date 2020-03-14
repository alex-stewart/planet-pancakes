import React, {Component} from 'react';
import {Table} from 'reactstrap';

const tableStyle = {
    width: 'unset'
};

const islandSizeNames = {
    1: 'Small',
    2: 'Medium',
    3: 'Large',
    4: 'Very Large'
};

export default class WikiStatBlock extends Component {

    render() {
        return <div>
            <Table style={tableStyle}>
                <tbody>
                <tr>
                    <th scope={"row"} className={"table-dark"}>Population</th>
                    <td className={"table-light"}>{this.props.island.population || '?'}</td>
                </tr>
                <tr>
                    <th scope={"row"} className={"table-dark"}>Size</th>
                    <td className={"table-light"}>{islandSizeNames[this.props.island.size]}</td>
                </tr>
                </tbody>
            </Table>
        </div>
    }
}
