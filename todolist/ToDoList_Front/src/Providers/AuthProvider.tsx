import React, { createContext, ReactNode } from "react"
import { useState } from "react";
import useLocalStorage from "../hooks/useLocalStorage";
import RequestHandler from "../RequestHandler";
import { useNavigate } from "react-router";
import { useAlert } from "../hooks/useAlert";
import { Alert, AlertType } from "../models/Alert/Alert"

interface AuthContextType {
	registrate: (emailValue:string, passwordValue:string) => Promise<void>;
	authorize: (emailValue:string, passwordValue:string) => Promise<void>;
	setAsUnauthorized: () => void;
	jwtToken:string
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
	const [jwtToken, setJwtToken] = useLocalStorage('jwtToken',"");
	const navigator = useNavigate();
	const {addAlert} = useAlert();

	const authorize = async (emailValue:string, passwordValue:string) => {
		try{
			const newToken = await RequestHandler.authorizeUser(emailValue,passwordValue);
			setJwtToken(newToken);
			navigator("/");
			
		}catch(e : any){
			addAlert(new Alert(AlertType.Error, e.message));
		}
	}

	const registrate = async (emailValue:string, passwordValue:string) => {
		try{
			const newToken = await RequestHandler.registerUser(emailValue, passwordValue);
			setJwtToken(newToken);
			navigator("/");
		}catch(e : any)
		{
			if (e.status == 401){
				setAsUnauthorized();
			}
			addAlert(new Alert(AlertType.Error, e.message));
		}
	}

	const setAsUnauthorized = () =>{
		setJwtToken("");
		navigator("/auth");
	}

	const value = {
		authorize,
		registrate,
		setAsUnauthorized,
		jwtToken
	};

	return (
	  <AuthContext.Provider value={value}>
		{children}
	  </AuthContext.Provider>
	);
};

export default AuthContext