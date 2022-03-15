import React, { Component } from 'react';
import './App.css';
import Table1 from './components/Table/Table'
import {withRouter} from "react-router-dom";

import {Head} from "./components/Header";



class App extends Component {

    render() {
        return (
            <div className="App">
                    <Head />
                    <Table1 />
            </div>
        );
    }
}

export default withRouter(App);