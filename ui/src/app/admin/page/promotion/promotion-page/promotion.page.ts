import {Component, OnInit} from "@angular/core";
import {PromotionModel} from "../../../model/promotion.model";
import {PromotionService} from "../../../service/promotion.service";
import {SelectionModel} from "@angular/cdk/collections";
import {MatDialog} from "@angular/material/dialog";
import {PromotionModalComponent} from "../../../component/promoton-modal/promotion-modal.component";
import {Sort} from "@angular/material/sort";
import {
  ConfirmDeleteModalComponent
} from "../../../component/confirm-delete-modal/confirm-delete-modal.component";

@Component({
  selector: 'app-promotion-main-page',
  templateUrl: 'promotion.page.html',
  styleUrls: ['promotion.page.less']
})
export class PromotionPage implements OnInit {

  promotions: PromotionModel[]
  displayedColumns: string[] = ['select', 'id', 'name', 'percentage', 'active', 'creationTime'];
  selection = new SelectionModel<PromotionModel>(true, []);

  constructor(private promotionService: PromotionService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.onRefresh()
  }

  isAllSelected() {
    const numSelected = this.selection.selected?.length;
    const numRows = this.promotions.length;
    return numSelected === numRows;
  }


  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.promotions.forEach(row => this.selection.select(row));
  }

  openPopupCreate() {
    const dialogRef = this.dialog.open(PromotionModalComponent, {
      width: '400px',
      data: {
        model: null,
        editMode: false
      }
    });
  }

  openPopupEdit() {
    const dialogRef = this.dialog.open(PromotionModalComponent, {
      width: '400px',
      data: {
        model: this.selection.selected[0],
        editMode: true
      }
    });
  }

  onEnable() {
    this.promotionService.enablePromotions(this.getSelectedIds()).subscribe(_ => {
      this.onRefresh()
    })
  }

  onDisable() {
    this.promotionService.disablePromotions(this.getSelectedIds()).subscribe(_ => {
      this.onRefresh()
    })
  }

  onDelete() {
    let text: string
    if(this.selection.selected.length > 1) {
      text = 'the promotions'
    } else {
      text = 'the promotion'
    }
    const confirmDialogRef = this.dialog.open(ConfirmDeleteModalComponent, {
      width: '300px',
      data: text
    })
    confirmDialogRef.afterClosed().subscribe(res => {
      if(res == 'confirm') {
        this.promotionService.deletePromotion(this.getSelectedIds()).subscribe(_ => {
          this.onRefresh()
        })
      }
    })
  }

  onRefresh() {
    this.promotionService.getAllPromotions().subscribe(res => {
      this.promotions = res
    })
    this.selection.clear()
  }

  sortData(sort: Sort) {
    const data = this.promotions.slice()
    if (!sort.active || sort.direction === '') {
      this.promotions = data
      return;
    }
    this.promotions = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc'
      switch (sort.active) {
        case 'id' : {
          return this.compare(a.id, b.id, isAsc)
        }
        case 'name': {
          return this.compare(a.name, b.name, isAsc)
        }
        case 'percentage': {
          return this.compare(a.percentage, b.percentage, isAsc)
        }
        case 'active': {
          return this.compare(a.active, b.active, isAsc)
        }
        case 'creationTimestamp': {
          return this.compare(a.creationTimestamp, b.creationTimestamp, isAsc)
        }
        default: {
          return 0
        }
      }
    })
  }

  compare(a: number | string | boolean | Date, b: number | string | boolean | Date, isAsc: boolean) {
    if (typeof a === 'boolean' && typeof b === 'boolean') {
      return (a === b ? 0 : (a ? 1 : -1)) * (isAsc ? 1 : -1);
    }

    if (a instanceof Date && b instanceof Date) {
      return (a.getTime() - b.getTime()) * (isAsc ? 1 : -1);
    }
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  private getSelectedIds() {
    return this.selection.selected.map(elem => {return elem.id})
  }
}
