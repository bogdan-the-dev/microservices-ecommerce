import {ActionWithPayload} from "../../shared/models/action-with-payload";
import {LoginAction} from "./login.action";

export interface LoginState {
  loginInProgress: boolean;
  token: string;
  role: string;
  username: string;
  success: boolean;
  error: boolean
  errorMessage: string
}

const loginState: LoginState = {
  loginInProgress: false,
  token: "",
  role: null,
  success: false,
  username: '',
  error: false,
  errorMessage: ''
}

export function LoginReducer (state= loginState, action: ActionWithPayload) {
  switch (action.type) {
    case LoginAction.LOGIN: {
      const newState = {...state}
      newState.loginInProgress = true;
      newState.username = action.payload.username
      newState.error = false
      newState.errorMessage = ''
      return newState;
    }
    case LoginAction.LOGIN_FINISHED: {
      const newState = {...state}
      newState.loginInProgress = false
      newState.token = action.payload.data.access_token
      newState.role = action.payload.data.role
      newState.success = action.payload.success
      newState.error = false;
      localStorage.setItem('token', newState.token)
      return newState
    }
    case LoginAction.LOGIN_ERROR: {
      const newState = {...state}
      newState.error = true
      newState.username = ''
      if(action.payload.data.status == 400) {
        newState.errorMessage = action.payload.data.error.message
      } else {
        newState.errorMessage = 'Invalid credentials.'
      }
      return newState
    }
    case LoginAction.LOGOUT: {
      const newState = {...state}
      newState.token = ''
      localStorage.removeItem('token')
      newState.success = false
      newState.role = ''
      newState.username = ''
      return newState
    }
    case LoginAction.RECOVER_STATE: {
      const newState = {...state}
      newState.token = action.payload
      return newState;
    }
    case LoginAction.RECOVER_STATE_FINISHED: {
      const newState = {...state}
      newState.role = action.payload.data.role
      newState.username = action.payload.data.username
      newState.success = true
      return newState
    }
    default: {
      return state
    }
  }
}
