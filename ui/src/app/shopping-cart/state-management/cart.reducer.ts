import {ItemCartModel} from "../model/item-cart.model";
import {ActionWithPayload} from "../../shared/models/action-with-payload";
import {CartAction} from "./cart.action";
import {bottom} from "@popperjs/core";

export interface CartState {
  items: Array<ItemCartModel>
  total: number
  numberOfItems: number
  saveOnline: boolean
}

const cartState: CartState = {
  items: new Array<ItemCartModel>(),
  total: 0,
  numberOfItems: 0,
  saveOnline: false
}

export function CartReducer (state = cartState, action: ActionWithPayload) {
  switch (action.type) {
    case CartAction.ADD_TO_CART_FINISHED: {
      let intermediaryState = {...state}
      let itemAdded = false
      if(intermediaryState.items.length === 0) {
        const newItem: ItemCartModel = action.payload
        intermediaryState.items = [newItem]
        itemAdded = true
      } else {
        intermediaryState.items = intermediaryState.items.map(item => {
          if (item.id === action.payload.id) {
            itemAdded = true
            return {...item, quantity: item.quantity + 1}
          }
          return item
        })
      }
      if(!itemAdded) {
        intermediaryState.items.push(action.payload)
      }
      intermediaryState.numberOfItems++
      intermediaryState.total += action.payload.price
      const newState = {...intermediaryState}
      return newState
    }
    case CartAction.REMOVE_FROM_CART_FINISHED: {
      const newState = {...state}
      const item = newState.items.find(item => item.id === action.payload.id)
      newState.items = newState.items.filter(elem => {return elem.id !== item.id})
      newState.numberOfItems -= item.quantity
      newState.total -= item.price * item.quantity
      return newState
    }
    case CartAction.GET_CART_FINISHED: {
      const newState = {...state}
      newState.items = action.payload
      action.payload.forEach((elem: ItemCartModel) => {
        newState.numberOfItems += elem.quantity
        newState.total += elem.price * elem.quantity
      })
      return newState
    }
    case CartAction.EMPTY_CART_FINISHED: {
      const newState = {...state}
      newState.total = 0
      newState.numberOfItems = 0
      newState.items = []
      return newState
    }
    case CartAction.SAVE_ONLINE: {
      const newState = {...state}
      newState.saveOnline = action.payload
      return newState
    }

    default: {
      return state
    }
  }
}
