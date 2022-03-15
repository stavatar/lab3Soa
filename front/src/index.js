import React from 'react'
import ReactDOM from 'react-dom'
import { NotificationContainer } from 'react-notifications';
//сначала подключем общие стили
import './index.css'
import 'react-notifications/lib/notifications.css';

import App from './App'
import {BrowserRouter, Route, Switch} from "react-router-dom";
import NameForm from './components/Forms/CreateForms'
ReactDOM.render(
    <BrowserRouter basename="/">
        <Switch>
            <Route  exact  path='/' component={App} />
            <Route  exact path='/add' component={NameForm} />
        </Switch>
        <NotificationContainer />

    </BrowserRouter> ,
    document.getElementById('root'));