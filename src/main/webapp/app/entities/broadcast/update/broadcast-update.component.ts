import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBroadcast, Broadcast } from '../broadcast.model';
import { BroadcastService } from '../service/broadcast.service';
import { IMatch } from 'app/entities/match/match.model';
import { MatchService } from 'app/entities/match/service/match.service';
import { ISystemConfiguration } from 'app/entities/system-configuration/system-configuration.model';
import { SystemConfigurationService } from 'app/entities/system-configuration/service/system-configuration.service';
import { IBroadcastPersonnelMember } from 'app/entities/broadcast-personnel-member/broadcast-personnel-member.model';
import { BroadcastPersonnelMemberService } from 'app/entities/broadcast-personnel-member/service/broadcast-personnel-member.service';

@Component({
  selector: 'jhi-broadcast-update',
  templateUrl: './broadcast-update.component.html',
})
export class BroadcastUpdateComponent implements OnInit {
  isSaving = false;

  matchesCollection: IMatch[] = [];
  systemConfigurationsSharedCollection: ISystemConfiguration[] = [];
  broadcastPersonnelMembersSharedCollection: IBroadcastPersonnelMember[] = [];

  editForm = this.fb.group({
    id: [],
    miscData: [],
    match: [null, Validators.required],
    systemConfiguration: [null, Validators.required],
    broadcastPersonnelMembers: [],
  });

  constructor(
    protected broadcastService: BroadcastService,
    protected matchService: MatchService,
    protected systemConfigurationService: SystemConfigurationService,
    protected broadcastPersonnelMemberService: BroadcastPersonnelMemberService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ broadcast }) => {
      this.updateForm(broadcast);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const broadcast = this.createFromForm();
    if (broadcast.id !== undefined) {
      this.subscribeToSaveResponse(this.broadcastService.update(broadcast));
    } else {
      this.subscribeToSaveResponse(this.broadcastService.create(broadcast));
    }
  }

  trackMatchById(_index: number, item: IMatch): number {
    return item.id!;
  }

  trackSystemConfigurationById(_index: number, item: ISystemConfiguration): number {
    return item.id!;
  }

  trackBroadcastPersonnelMemberById(_index: number, item: IBroadcastPersonnelMember): number {
    return item.id!;
  }

  getSelectedBroadcastPersonnelMember(
    option: IBroadcastPersonnelMember,
    selectedVals?: IBroadcastPersonnelMember[]
  ): IBroadcastPersonnelMember {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBroadcast>>): void {
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

  protected updateForm(broadcast: IBroadcast): void {
    this.editForm.patchValue({
      id: broadcast.id,
      miscData: broadcast.miscData,
      match: broadcast.match,
      systemConfiguration: broadcast.systemConfiguration,
      broadcastPersonnelMembers: broadcast.broadcastPersonnelMembers,
    });

    this.matchesCollection = this.matchService.addMatchToCollectionIfMissing(this.matchesCollection, broadcast.match);
    this.systemConfigurationsSharedCollection = this.systemConfigurationService.addSystemConfigurationToCollectionIfMissing(
      this.systemConfigurationsSharedCollection,
      broadcast.systemConfiguration
    );
    this.broadcastPersonnelMembersSharedCollection = this.broadcastPersonnelMemberService.addBroadcastPersonnelMemberToCollectionIfMissing(
      this.broadcastPersonnelMembersSharedCollection,
      ...(broadcast.broadcastPersonnelMembers ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.matchService
      .query({ filter: 'broadcast-is-null' })
      .pipe(map((res: HttpResponse<IMatch[]>) => res.body ?? []))
      .pipe(map((matches: IMatch[]) => this.matchService.addMatchToCollectionIfMissing(matches, this.editForm.get('match')!.value)))
      .subscribe((matches: IMatch[]) => (this.matchesCollection = matches));

    this.systemConfigurationService
      .query()
      .pipe(map((res: HttpResponse<ISystemConfiguration[]>) => res.body ?? []))
      .pipe(
        map((systemConfigurations: ISystemConfiguration[]) =>
          this.systemConfigurationService.addSystemConfigurationToCollectionIfMissing(
            systemConfigurations,
            this.editForm.get('systemConfiguration')!.value
          )
        )
      )
      .subscribe((systemConfigurations: ISystemConfiguration[]) => (this.systemConfigurationsSharedCollection = systemConfigurations));

    this.broadcastPersonnelMemberService
      .query()
      .pipe(map((res: HttpResponse<IBroadcastPersonnelMember[]>) => res.body ?? []))
      .pipe(
        map((broadcastPersonnelMembers: IBroadcastPersonnelMember[]) =>
          this.broadcastPersonnelMemberService.addBroadcastPersonnelMemberToCollectionIfMissing(
            broadcastPersonnelMembers,
            ...(this.editForm.get('broadcastPersonnelMembers')!.value ?? [])
          )
        )
      )
      .subscribe(
        (broadcastPersonnelMembers: IBroadcastPersonnelMember[]) =>
          (this.broadcastPersonnelMembersSharedCollection = broadcastPersonnelMembers)
      );
  }

  protected createFromForm(): IBroadcast {
    return {
      ...new Broadcast(),
      id: this.editForm.get(['id'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      match: this.editForm.get(['match'])!.value,
      systemConfiguration: this.editForm.get(['systemConfiguration'])!.value,
      broadcastPersonnelMembers: this.editForm.get(['broadcastPersonnelMembers'])!.value,
    };
  }
}
