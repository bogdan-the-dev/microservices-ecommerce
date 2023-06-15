import {Action} from "@ngrx/store";
import {ActionWithPayload} from "../models/action-with-payload";

export class Convert {
  static ToPayload(action: ActionWithPayload): any {
    return action.payload
  }
}
