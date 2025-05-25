import React, { createContext, useContext } from "react"
import {createRoot} from "react-dom/client";
import { BrowserRouter, Routes } from "react-router"

import App from "./components/App"
import "./css/main.css"

const rootElement = document.getElementById("root");

if (!rootElement) throw new Error('Failed to find the root element');

const AuhorizationContext = createContext("Au");

const root = createRoot(rootElement);

root.render(
	<BrowserRouter>
		<App />
	</BrowserRouter>


);