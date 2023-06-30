import {ActionReducerMap} from "@ngrx/store";
import {CartReducer} from "./cart.reducer";

export interface CartModuleState {
  cartModuleState: CartModuleState
}

export const cartReducerMap: ActionReducerMap<CartModuleState> = {
  //@ts-ignore
  cartState: CartReducer
}

export const cartFeatureName = 'cartModuleFeature'
