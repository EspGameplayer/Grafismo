import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBroadcastPersonnelMember, BroadcastPersonnelMember } from '../broadcast-personnel-member.model';
import { BroadcastPersonnelMemberService } from '../service/broadcast-personnel-member.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { BroadcastPersonnelMemberRole } from 'app/entities/enumerations/broadcast-personnel-member-role.model';

@Component({
  selector: 'jhi-broadcast-personnel-member-update',
  templateUrl: './broadcast-personnel-member-update.component.html',
})
export class BroadcastPersonnelMemberUpdateComponent implements OnInit {
  isSaving = false;
  broadcastPersonnelMemberRoleValues = Object.keys(BroadcastPersonnelMemberRole);

  peopleCollection: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    role: [],
    person: [null, Validators.required],
  });

  constructor(
    protected broadcastPersonnelMemberService: BroadcastPersonnelMemberService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ broadcastPersonnelMember }) => {
      this.updateForm(broadcastPersonnelMember);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const broadcastPersonnelMember = this.createFromForm();
    if (broadcastPersonnelMember.id !== undefined) {
      this.subscribeToSaveResponse(this.broadcastPersonnelMemberService.update(broadcastPersonnelMember));
    } else {
      this.subscribeToSaveResponse(this.broadcastPersonnelMemberService.create(broadcastPersonnelMember));
    }
  }

  trackPersonById(_index: number, item: IPerson): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBroadcastPersonnelMember>>): void {
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

  protected updateForm(broadcastPersonnelMember: IBroadcastPersonnelMember): void {
    this.editForm.patchValue({
      id: broadcastPersonnelMember.id,
      role: broadcastPersonnelMember.role,
      person: broadcastPersonnelMember.person,
    });

    this.peopleCollection = this.personService.addPersonToCollectionIfMissing(this.peopleCollection, broadcastPersonnelMember.person);
  }

  protected loadRelationshipsOptions(): void {
    this.personService
      .query({ filter: 'broadcastpersonnelmember-is-null' })
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing(people, this.editForm.get('person')!.value)))
      .subscribe((people: IPerson[]) => (this.peopleCollection = people));
  }

  protected createFromForm(): IBroadcastPersonnelMember {
    return {
      ...new BroadcastPersonnelMember(),
      id: this.editForm.get(['id'])!.value,
      role: this.editForm.get(['role'])!.value,
      person: this.editForm.get(['person'])!.value,
    };
  }
}
