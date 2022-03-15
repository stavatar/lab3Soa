import React, { Component } from 'react';
import {BootstrapTable,
    TableHeaderColumn} from 'react-bootstrap-table';
import InsertButton from 'react-bootstrap-table/lib/toolbar/InsertButton';
import DeleteButton from 'react-bootstrap-table/lib/toolbar/DeleteButton';
import '../../css/Table.css';
import 'react-bootstrap-table/css/react-bootstrap-table.css'
import axios from "axios";
import {onAfterDeleteRow,sendEditCell,addTeam,makeDepressiveTeam} from "../../apiNet"
import {withRouter} from "react-router-dom";
import {path_server} from "../../apiNet";
import {CNav, CNavItem} from "@coreui/react";
import ReactPaginate from 'react-paginate';
import {mood,weaponType,bool_val} from "../../const";
import 'react-bootstrap-table/dist/react-bootstrap-table.min.css';
import qs from 'qs';
import {Button, Modal} from "react-bootstrap";
import NameForm from "../Forms/CreateForms";
import 'bootstrap/dist/css/bootstrap.min.css';
import TableTeams from "./TableTeams";
import './../../index.css'

function formatterCar(cell, row) {
    return cell.cool;
}

function formatterX(cell, row) {
    if (row.coordinates===null || row.coordinates.x===null)
        return <span>0</span>

    return <span>{row.coordinates.x}</span>
}
function formatterY(cell, row) {
    if (row.coordinates===null || row.coordinates.y==null)
        return <span>0</span>
    return <span>{row.coordinates.y}</span>
}



class Table extends Component {
    constructor(props) {
        super(props);
        axios.defaults.validateStatus = () => {
            return true;
        };
        this.handlePageClick=this.handlePageClick.bind(this);
        this.handleChangeSizePage=this.handleChangeSizePage.bind(this);
        this.handleKeyPress=this.handleKeyPress.bind(this);
        this.getAllObject=this.getAllObject.bind(this);
        this.onFilterChange=this.onFilterChange.bind(this);
        this.onSortChange=this.onSortChange.bind(this);
        this.handleChangeTeamName=this.handleChangeTeamName.bind(this);


        this.onRowSelect=this.onRowSelect.bind(this);
        this.handleClose=this.handleClose.bind(this);
        this.handleShow=this.handleShow.bind(this);

        this.handleCloseTeams=this.handleCloseTeams.bind(this);
        this.handleShowTeam=this.handleShowTeam.bind(this);
        this.state = {data:[],
                      sizePage:10,
                      allSizeList:1,
                      currentPage:1,
                      sort:{nameField:'id',isAscending:true},
                      filter:[],
                      selectedRow:new Set(),
                      nameTeam:'',
                      nameTeamForDepressive:'',
                      showAdd: false,
                      showTeams: false,
                      handleClose:false,
                      handleCloseTeams:false
                      } ;
    }
    handleChangeTeamName(event){
        this.setState({nameTeam: event.target.value});

    }

    componentDidMount() {
        this.getAllObject();
        this.timer = setInterval(() => this.getAllObject(), 100000);

    }
    componentWillUnmount() {
        this.timer = null;
    }beforeClose(e) {}


    handlePageClick (data){
        let curPage=data.selected+1;
        this.setState({currentPage:curPage },this.getAllObject);
    }
    handleChangeSizePage(event){
        this.setState({sizePage: event.target.value,currentPage:1 },this.getAllObject);
        event.preventDefault();
    }
    handleKeyPress(event){
        if (event.key === "Enter") {
            this.getAllObject();
        }
        event.preventDefault();
    }
    createCustomInsertButton = () => {
        return (
            <div className="">

                <div className="">
                    <label >Name team</label>
                    <input  type="text" value={this.state.nameTeam} onChange={this.handleChangeTeamName} />

                <InsertButton
                    className="btn-padding-left-top"
                    value='Add Command'
                    size=""
                    onClick={ () => {
                                        debugger;
                                        this.setState(({selectedRow}) => ({selectedRow: new Set()}));
                                        addTeam(this.state.selectedRow,this.state.nameTeam)
                                    }
                            }>Create  team with selected human</InsertButton>
                </div>
                <br/>

                <InsertButton
                    variant="primary"
                    className="btn-padding-left-top"
                    value='Create human'
                    size=""
                    onClick={ () => {
                                         debugger;
                                        this.setState(({selectedRow}) => ({selectedRow: new Set()}));
                                         this.setState({showAdd : true});
                                        this.setState({showAdd : true});
                                        //this.props.history.push("/add")
                                    }
                            }>Create  human</InsertButton>
            </div>
        );
    }
    createCustomDeleteButton = (onClick) => {
        return (
            <div className="margin">
                <InsertButton
                    variant="primary"
                    className="btn-padding-left-top"
                    value='Show teams'
                    size=""
                    onClick={ () => {
                        debugger;
                        this.setState({showTeams : true});
                        this.setState({showTeams : true});
                    }
                    }>Show teams</InsertButton>

                <DeleteButton
                    size=""
                    btnText='Delete selected human'
                    className="btn-padding-left-top"
                    onClick={ e => {
                                        this.setState(({selectedRow}) => ({selectedRow: new Set()}));
                                        onClick();
                                    }
                            }/>
            </div>
     )};

    onSortChange = (sortName, sortOrder) => {
        debugger;
        let sortFlag = sortOrder==="asc";
        this.setState({sort:{nameField:sortName,isAscending:sortFlag}},this.getAllObject);
        debugger;
    }

    onFilterChange(filterObj) {
        let arr=[];
        let filterValue;
        for (const key in filterObj) {
            let current={nameField:'',action:'',value:''}
            current.nameField=key;
            switch (filterObj[key].type) {
                case 'DateFilter':{
                    debugger;
                    let filterValue = filterObj[key].value.date;
                    current.value=filterValue;
                    let comparator = filterObj[key].value.comparator;
                    current.action=comparator;
                    debugger;
                    break;
                }
                case 'NumberFilter': {
                    debugger;
                    let filterValue = filterObj[key].value.number;
                    current.value=filterValue;
                    let comparator = filterObj[key].value.comparator;
                    current.action=comparator;
                    debugger;
                    break;
                }
                default: {
                    filterValue = filterObj[key].value;
                    current.value=filterValue;
                    debugger;
                    break;
                }
            }
            arr.push(current);
            debugger;
        }
        this.setState({filter:arr},this.getAllObject);
    }
    onAfterSaveCell(row, cellName, cellValue) {
        debugger;
        sendEditCell(row, cellName, cellValue)

    }
    remote(remoteObj) {
        remoteObj.sort=true;
        remoteObj.filter=true;
        return remoteObj;
    }

    onRowSelect(row, isSelected, e) {
        debugger;
        if(isSelected) {
            this.setState(({selectedRow}) => ({
                selectedRow: new Set(selectedRow).add(row)
            }));
        }
        else{
            if(this.state.selectedRow.size<=1){
                this.setState(({selectedRow}) => ({
                    selectedRow: new Set()
                }));
            }else {
                new Set(this.state.selectedRow).delete(row);
                this.setState(({selectedRow}) => ({
                    selectedRow: new Set(this.state.selectedRow)
                }));
            }
        }

    }
    handleClose(){
        this.setState({showAdd:false});
    }
    handleShow(){
        this.setState({showAdd:true});
    }

    handleCloseTeams(){
        debugger;
        this.setState({showTeams:false});
    }
    handleShowTeam(){
        debugger;
        this.setState({showTeams:true});
    }
    render(){
        const options = {
            hideSizePerPage: true,
            sizePerPage: 5,
            deleteBtn: this.createCustomDeleteButton,
            insertBtn: this.createCustomInsertButton,
            onSortChange: this.onSortChange,
            onFilterChange: this.onFilterChange,
            afterDeleteRow: onAfterDeleteRow,// A hook for after insert rows
        };
        const selectRowProp = {
            mode: 'checkbox',
            columnWidth: '2%',
            onSelect: this.onRowSelect,
        };
        const cellEditProp = {
            mode: 'dbclick', // 'dbclick' for trigger by double-click
            afterSaveCell: sendEditCell,
            blurToSave: true
        }


        const currentPage=Math.ceil(this.state.allSizeList/this.state.sizePage);
        debugger;
        return (
                <div>
                <BootstrapTable
                                remote
                                data={this.state.data}
                                dataField='id'
                                tdStyle={ { whiteSpace: 'normal' } }
                                insertRow
                                cellEdit={cellEditProp}
                                selectRow={ selectRowProp }
                                deleteRow={ true }
                                options={ options }
                                striped
                                hover
                                containerClass='table'
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
                    <TableHeaderColumn dataField='impactSpeed'
                                       dataSort
                                       filter={ { type: 'NumberFilter' } }
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="5%"

                                       editable={ { type: 'number' } }>
                        impactSpeed
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='weaponType'
                                       dataSort
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="5%"
                                       filter={ { type: 'SelectFilter', options: weaponType} }
                                       editable={ { type: 'select', options: { values: weaponType } } }>
                        weaponType
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='mood'
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="5%"
                                       dataSort={ true }
                                       filter={ { type: 'SelectFilter', options: mood} }
                                       editable={ { type: 'select', options: { values: mood } } }>
                        moodType
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='X'
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="5%"
                                       dataSort
                                       dataFormat={formatterX}
                                       filter={ { type: 'NumberFilter' } }
                                       editable={ { type: 'number' }} >
                        x
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='Y'
                                       dataAlign='center'
                                       dataSort
                                       headerAlign="center"
                                       width="5%"
                                       dataFormat={formatterY}
                                       filter={ { type: 'NumberFilter' } }
                                       editable={ { type: 'number' , options: { min: '0', max: '369' } } }>
                        y
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='creationDate'
                                       dataAlign='center'
                                       dataSort
                                       headerAlign="center"
                                       width="7%"
                                       editable={ false }
                                       filter={ { type: 'DateFilter' } }
                        >
                        date

                    </TableHeaderColumn>

                    <TableHeaderColumn dataField='realHero'
                                       dataSort
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="2%"
                                       filter={ { type: 'SelectFilter', options: bool_val} }
                                       editable={ { type: 'checkbox'  }}>
                        R
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='car'
                                       dataSort
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="2%"
                                       dataFormat={formatterCar}
                                       filter={ { type: 'SelectFilter', options: bool_val} }
                                       editable={ { type: 'checkbox'  }}>
                        C
                    </TableHeaderColumn>


                </BootstrapTable>
                    <CNav className="background-nav justify-content-lg-between">
                        <CNavItem className=" ">
                                <input  name="sizePage" type="number" onKeyPress={this.handleKeyPress}   value={this.state.sizePage} onChange={this.handleChangeSizePage} />
                        </CNavItem>
                        <CNavItem className=" ">
                            <ReactPaginate
                                previousLabel={"← Previous"}
                                nextLabel={"Next →"}
                                pageCount={currentPage}
                                onPageChange={this.handlePageClick}
                                containerClassName={"pagination"}
                                previousLinkClassName={"pagination__link"}
                                nextLinkClassName={"pagination__link"}
                                disabledClassName={"pagination__link--disabled"}
                                activeClassName={"pagination__link--active"}
                            />
                        </CNavItem>
                    </CNav>
                    <Modal show={this.state.showAdd} onHide={this.handleClose} animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title>Modal heading</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <NameForm/>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleClose}>
                                Close
                            </Button>
                        </Modal.Footer>
                    </Modal>

                    <Modal size="lg" show={this.state.showTeams} onHide={this.handleCloseTeams} animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title>Modal heading</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <TableTeams/>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleShowTeam}>
                                Close
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </div>
        );
    }

    getAllObject() {

        let arr_filt=JSON.stringify(this.state.filter);
        if(arr_filt==='[]')
        if(arr_filt==='[]')
            arr_filt=null;

        axios.get(path_server+"humanBeings", {
            params:
                {
                    sizePage: this.state.sizePage,
                    numberPage: this.state.currentPage,
                    sortField:this.state.sort.nameField,
                    isAsc:this.state.sort.isAscending,
                    filters:arr_filt
                },
            paramsSerializer: params => {
                return qs.stringify(params)
            }})
            .then(res => {
                const msg = res.data;
                if(msg.code ===1){
                    let k=msg.data;
                    this.setState({data: k,allSizeList: msg.allSizeList});
                }
            }).catch(err => {
            // what now?
            console.log(err);
        });
    }
}

export default withRouter(Table);