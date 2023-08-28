import {Component} from "@angular/core";
import {AdminAccountGridModel} from "../../model/admin-account-grid.model";
import {SelectionModel} from "@angular/cdk/collections";
import {ProductTableModel} from "../../model/product-table.model";
import {MatDialog} from "@angular/material/dialog";
import {Sort} from "@angular/material/sort";
import {LoginService} from "../../../login/service/login.service";
import {ProductModalComponent} from "../../component/product-modal/product-modal.component";
import {CreateAdminModalComponent} from "../../component/create-admin-account-modal/create-admin-modal.component";

@Component({
  selector: 'app-create-admin-account.page',
  templateUrl: 'create-admin-account.page.html',
  styleUrls: ['create-admin-account.page.less']
})
export class CreateAdminAccountPage {
  accounts: AdminAccountGridModel[]
  displayedColumns: string[] = ['select', 'username', 'email', 'account state']
  selection = new SelectionModel<AdminAccountGridModel>(true, [])
  searchKeyword: string = ''
  filteredAccounts: AdminAccountGridModel[] = []

  constructor(private dialog: MatDialog, private loginService: LoginService) {
  }

  ngOnInit() {
    this.onRefresh()
  }

  onCreate() {
    const dialogRef = this.dialog.open(CreateAdminModalComponent, {
      width: '600px',
      data: {
      }
    })
  }

  isAllSelected() {
    const numSelected = this.selection.selected?.length;
    const numRows = this.filteredAccounts?.length;
    return numSelected === numRows && numRows > 0;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.filteredAccounts.forEach(row => this.selection.select(row));
  }

  onEnable() {
    this.loginService.EnableAdmin(this.selection.selected.map(elem => elem.username)).subscribe(_ => {
      this.onRefresh();
    })
  }

  onDisable() {
    this.loginService.DisableAdmin(this.selection.selected.map(elem => elem.username)).subscribe(_ => {
      this.onRefresh();
    })
  }
  onDelete() {
    this.loginService.DeleteAdmin(this.selection.selected.map(elem => elem.username)).subscribe(_ => {
      this.onRefresh();
    })
  }
  onRefresh() {
    this.loginService.GetAdmins().subscribe(res => {
      this.accounts = res
      this.applyFilter()
    })
    this.selection.clear()
  }

  sortData(sort: Sort) {
    const data = this.filteredAccounts.slice()
    if (!sort.active || sort.direction === '') {
      this.filteredAccounts = data
      return;
    }
    this.filteredAccounts = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc'
      switch (sort.active) {
        case 'username': {
          return this.compare(a.username, b.username, isAsc)
        }
        case 'email': {
          return this.compare(a.email, b.email, isAsc)
        }
        case 'account state': {
          return  this.compare(a.isEnabled, b.isEnabled, isAsc)
        }
        default: {
          return 0
        }
      }
    })
  }

  compare(a: string | boolean, b: string | boolean, isAsc: boolean) {
    if (typeof a === 'boolean' && typeof b === 'boolean') {
      return (a === b ? 0 : (a ? 1 : -1)) * (isAsc ? 1 : -1);
    }
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  applyFilter() {
    this.filteredAccounts = []
    this.filteredAccounts = this.accounts.filter(elem => {
      if(this.searchKeyword == '') {
        return true
      }
      return elem.username.trim().toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
    })
  }
}
