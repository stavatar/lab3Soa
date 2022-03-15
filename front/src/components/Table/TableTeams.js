import React, { Component } from 'react';
import {BootstrapTable,
    TableHeaderColumn} from 'react-bootstrap-table';
import InsertButton from 'react-bootstrap-table/lib/toolbar/InsertButton';
import DeleteButton from 'react-bootstrap-table/lib/toolbar/DeleteButton';
import '../../css/Table.css';
import 'react-bootstrap-table/css/react-bootstrap-table.css'
import axios from "axios";
import {onAfterDeleteRow, sendEditCell, addTeam, makeDepressiveTeam, path_server} from "../../apiNet"
import {withRouter} from "react-router-dom";
import {path_server2} from "../../apiNet";

import 'react-bootstrap-table/dist/react-bootstrap-table.min.css';
import qs from 'qs';
import {Button, Modal} from "react-bootstrap";
import NameForm from "../Forms/CreateForms";
import 'bootstrap/dist/css/bootstrap.min.css';
import {CNav, CNavItem} from "@coreui/react";
import ReactPaginate from "react-paginate";

;
function formatterTeam(cell, row) {
    if (cell===null)
        return <span>-</span>
    debugger;
    return <div>
        <span>Id: {cell[0].id}</span>
        <br/>
        <span>Name: {cell[0].name}</span>
    </div>
}
class TableTeams extends React.Component{
    constructor(props) {
        super(props);
        debugger;
        console.log("!!!+!+!+!+!+!+!+!+!+!+!+!+!+!+!++!+!!+!+");

        this.getAllObject=this.getAllObject.bind(this);

        this.state = { data:[{id:0,name:"","team":"-"}],
                       selectedRow:new Set(),
                       nameTeamForDepressive:'',
                        sizePage:5,
                        currentPage:1
        };

        this.handleChangeTeamNameForDepressive=this.handleChangeTeamNameForDepressive.bind(this);
    }

    handleChangeTeamNameForDepressive(event){
        this.setState({nameTeamForDepressive: event.target.value});

    }
    createCustomDeleteButton = (onClick) => {
        return (
            <div className="margin">
                <label >Name team</label>
                <input  type="text" value={this.state.nameTeamForDepressive} onChange={this.handleChangeTeamNameForDepressive} />
                <DeleteButton
                    size=""
                    btnText='Make depressive team'
                    className="btn-padding-left-top"
                    onClick={ e => {   this.setState(({selectedRow}) => ({selectedRow: new Set()}));
                        makeDepressiveTeam(this.state.nameTeamForDepressive
                        ) }}> makeDepressiveTeam</DeleteButton>
                <br/>
                <br/>
            </div>
        )};
    componentDidMount() {
        debugger;
        this.getAllObject();
        this.timer = setInterval(() => this.getAllObject(), 100000);
    }
    render() {
        const options = {
            deleteBtn: this.createCustomDeleteButton,
            sizePerPage: 5,
            hideSizePerPage: true
        };

        return ( <div> <BootstrapTable
            data={this.state.data}
            dataField='id'
            deleteRow={ true }
            options={ options }
            pagination
        >
            <TableHeaderColumn isKey
                               dataSort
                               dataField='id'
                               headerAlign="left"
                               width="1%"
            >
                ID
            </TableHeaderColumn>

            <TableHeaderColumn dataField='name'
                               dataSort
                               dataAlign='center'
                               headerAlign="center"
                               width="4%"
                               filter={ { type: 'TextFilter' } }
            >
                Name
            </TableHeaderColumn>

                <TableHeaderColumn dataField='team'
                                   dataSort
                                   dataAlign='center'
                                   headerAlign="center"
                                   width="2%"
                                   dataFormat={formatterTeam}
                >
                    Team
                </TableHeaderColumn>
        </BootstrapTable>

            </div>
        )
    }

    getAllObject() {
        axios.get(path_server2+"all")
            .then(res => {
                const msg = res.data;
                debugger;
                if(msg.code ===1){
                    let k=msg.data;
                    console.log("K====="+ k)
                    this.setState({data: k});
                    debugger;
                }
            }).catch(err => {
                console.log(err);
        });
    }


}

export default withRouter(TableTeams);