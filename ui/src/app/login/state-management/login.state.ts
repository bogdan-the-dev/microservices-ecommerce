import {LoginReducer, LoginState} from "./login.reducer";
import {ActionReducerMap} from "@ngrx/store";

export interface LoginModuleState {
  loginModuleState: LoginState;
}

export const loginReducersMap: ActionReducerMap<LoginModuleState> = {
// @ts-ignore
  loginState: LoginReducer
};

export const loginFeatureName = 'loginModuleFeature';
