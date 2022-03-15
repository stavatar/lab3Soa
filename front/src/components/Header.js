import { CNav, CNavItem} from "@coreui/react";

import '../css/Header.css';
import {countMood,calcAverage,deleteMood} from "../apiNet";
import React from "react";
import {mood} from "../const";


function  handleCalculate() {
    debugger;
    calcAverage();
}
function  handleCount(mood) {
    debugger;
    countMood(mood);
}
function handleDelete(mood) {
    debugger;
    deleteMood(mood);

}
export  class Head extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            typeMood:'SORROW',
        };

        this.handleChange = this.handleChange.bind(this);
    }
    handleChange(event,text) {
        debugger;
        this.setState({typeMood: event.target.value});
    }
    render() {
        return (
            <CNav className="background-nav justify-content-lg-between">
                <CNavItem className=" ">
                    <div className="margin-top-5 my-btn-group">
                        <input type="button"
                               className="left btn-lg bgFont myButton"
                               value='Calculate'
                               size=""
                               onClick={() => handleCalculate()}/>
                    </div>
                </CNavItem>
                <CNavItem className="width-300">
                    <div  className="btn-group">
                        <div className="my-btn-group">

                                <input type="button"
                                       className="btn-lg smFont myButton"
                                       value='Count'
                                       size=""
                                       onClick={() => handleCount(this.state)}/>

                                <input type="button"
                                       className="btn-lg smFont myButton"
                                       value='Delete '
                                       size=""
                                       onClick={() => handleDelete(this.state,this.props.history)}/>
                            </div>

                        <select className="" name="typeMood" value={this.state.typeMood}
                                onChange={this.handleChange}>
                            {
                                mood.map(name => (<option key={name} value={name}> {name}</option>))
                            }
                        </select>

                    </div>
                </CNavItem>
            </CNav>
        );
    }
}