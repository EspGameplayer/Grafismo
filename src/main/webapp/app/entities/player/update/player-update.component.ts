import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPlayer, Player } from '../player.model';
import { PlayerService } from '../service/player.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IPosition } from 'app/entities/position/position.model';
import { PositionService } from 'app/entities/position/service/position.service';
import { Side } from 'app/entities/enumerations/side.model';

@Component({
  selector: 'jhi-player-update',
  templateUrl: './player-update.component.html',
})
export class PlayerUpdateComponent implements OnInit {
  isSaving = false;
  sideValues = Object.keys(Side);

  peopleCollection: IPerson[] = [];
  positionsSharedCollection: IPosition[] = [];

  editForm = this.fb.group({
    id: [],
    shirtName: [],
    height: [],
    weight: [],
    strongerFoot: [],
    preferredSide: [],
    contractUntil: [],
    retirementDate: [],
    miscData: [],
    person: [null, Validators.required],
    positions: [],
  });

  constructor(
    protected playerService: PlayerService,
    protected personService: PersonService,
    protected positionService: PositionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ player }) => {
      this.updateForm(player);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const player = this.createFromForm();
    if (player.id !== undefined) {
      this.subscribeToSaveResponse(this.playerService.update(player));
    } else {
      this.subscribeToSaveResponse(this.playerService.create(player));
    }
  }

  trackPersonById(_index: number, item: IPerson): number {
    return item.id!;
  }

  trackPositionById(_index: number, item: IPosition): number {
    return item.id!;
  }

  getSelectedPosition(option: IPosition, selectedVals?: IPosition[]): IPosition {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlayer>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(player: IPlayer): void {
    this.editForm.patchValue({
      id: player.id,
      shirtName: player.shirtName,
      height: player.height,
      weight: player.weight,
      strongerFoot: player.strongerFoot,
      preferredSide: player.preferredSide,
      contractUntil: player.contractUntil,
      retirementDate: player.retirementDate,
      miscData: player.miscData,
      person: player.person,
      positions: player.positions,
    });

    this.peopleCollection = this.personService.addPersonToCollectionIfMissing(this.peopleCollection, player.person);
    this.positionsSharedCollection = this.positionService.addPositionToCollectionIfMissing(
      this.positionsSharedCollection,
      ...(player.positions ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.personService
      .query({ filter: 'player-is-null' })
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing(people, this.editForm.get('person')!.value)))
      .subscribe((people: IPerson[]) => (this.peopleCollection = people));

    this.positionService
      .query()
      .pipe(map((res: HttpResponse<IPosition[]>) => res.body ?? []))
      .pipe(
        map((positions: IPosition[]) =>
          this.positionService.addPositionToCollectionIfMissing(positions, ...(this.editForm.get('positions')!.value ?? []))
        )
      )
      .subscribe((positions: IPosition[]) => (this.positionsSharedCollection = positions));
  }

  protected createFromForm(): IPlayer {
    return {
      ...new Player(),
      id: this.editForm.get(['id'])!.value,
      shirtName: this.editForm.get(['shirtName'])!.value,
      height: this.editForm.get(['height'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      strongerFoot: this.editForm.get(['strongerFoot'])!.value,
      preferredSide: this.editForm.get(['preferredSide'])!.value,
      contractUntil: this.editForm.get(['contractUntil'])!.value,
      retirementDate: this.editForm.get(['retirementDate'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      person: this.editForm.get(['person'])!.value,
      positions: this.editForm.get(['positions'])!.value,
    };
  }
}
