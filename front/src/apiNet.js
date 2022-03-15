import axios from "axios";
import {NotificationManager} from "react-notifications";
//export const path_server="http://localhost:8181/";

export const path_server="https://localhost:8181/LABSOA1_war/";
export const path_server2="https://localhost:8182/lab/tean/";

//export  const path_server = "http://desktop-biptr25:8085/Servlets-1.0-SNAPSHOT/";
//export  const path_server = "http://localhost:8181/Servlets-1.0-SNAPSHOT";


function printMessages(msg){
    debugger;
    if(msg.code===0){
        NotificationManager.error( msg.data, 'Ошибка',2000);
    }else {
        NotificationManager.success( msg.data, 'Успех!',2000);
    }
}
export function deleteMood(mood,hist){
    debugger;

    axios.post(path_server+"deleteAllForMood",null,{params:{"mood": String(mood.typeMood)}})
        .then(res => {

            const msg = res.data;
            printMessages(msg);
            window.location.reload();
        })

}


export function calcAverage(){
    axios.get(path_server+"averageSpeed")
        .then(res => {
            debugger;
            const msg = res.data;
            printMessages(msg);
        })
}

export function  countMood(mood){
    axios.get(path_server+"countHuman",{params:{"mood": String(mood.typeMood)}})
        .then(res => {
            debugger;
            const msg = res.data;
            printMessages(msg);
        })
}

export  function addObj(new_obj,hist){
    new_obj.coordinates={
                            x:new_obj.X,
                            y:new_obj.Y
                         }
    debugger;
    axios.post(path_server+"humanBeings/", new_obj)
        .then(res => {
            debugger;
            const msg = res.data;
            printMessages(msg);
        }).catch(err => {
        // what now?
        debugger;
        console.log(err);
    })
}
export  function addTeam(new_obj,name_team){
    debugger;
    let ids=[];
    for (let human of new_obj) {
        ids.push(human.id);
    }

    axios.post(path_server2+"create/id/name", {
        "nameTeam":name_team,
        "idhumans": ids
    })
        .then(res => {
            debugger;
            const msg = res.data;
            debugger;
        })
}
export  function makeDepressiveTeam(id){
    debugger;
    axios.put(path_server2+id+"/make-depressive", {})
        .then(response => {
            console.log(response);
        })
}
export  function onAfterDeleteRow(row) {
    debugger;

    let g = row.length;
    for(let i=0;i<g;i++){
        axios.delete(path_server+"humanBeings/"+row[i])
            .then(res => {
                debugger;
                const msg = res.data;
                printMessages(msg);
                window.location.reload();

            })
    }
    debugger;
}
export  function sendEditCell(row, cellName, cellValue) {
    debugger;
    axios.put(path_server+"humanBeings",{id:row["id"],fieldName:cellName,newValue: cellValue})
        .then(res => {
            debugger;
            const msg = res.data;
            printMessages(msg);
            window.location.reload();
        })

}