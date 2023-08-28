import {Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AdminApiUrls} from "./admin.api-urls";
import {InventoryModel} from "../model/inventory.model";

@Injectable({
  providedIn: "root"
})
export class InventoryService {

  constructor(private http: HttpClient) {
  }

  GetInventories() {
    return this.http.get<InventoryModel[]>(AdminApiUrls.getInventory)
  }

  EditInventory(inventoryId: string, quantity: number) {
    return this.http.put(AdminApiUrls.editInventory + '?id=' + inventoryId + '&qty=' + quantity,{})
  }

}
