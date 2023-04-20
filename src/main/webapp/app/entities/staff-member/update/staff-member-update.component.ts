import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStaffMember, StaffMember } from '../staff-member.model';
import { StaffMemberService } from '../service/staff-member.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { StaffMemberRole } from 'app/entities/enumerations/staff-member-role.model';

@Component({
  selector: 'jhi-staff-member-update',
  templateUrl: './staff-member-update.component.html',
})
export class StaffMemberUpdateComponent implements OnInit {
  isSaving = false;
  staffMemberRoleValues = Object.keys(StaffMemberRole);

  peopleCollection: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    defaultRole: [],
    contractUntil: [],
    retirementDate: [],
    miscData: [],
    person: [null, Validators.required],
  });

  constructor(
    protected staffMemberService: StaffMemberService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ staffMember }) => {
      this.updateForm(staffMember);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const staffMember = this.createFromForm();
    if (staffMember.id !== undefined) {
      this.subscribeToSaveResponse(this.staffMemberService.update(staffMember));
    } else {
      this.subscribeToSaveResponse(this.staffMemberService.create(staffMember));
    }
  }

  trackPersonById(_index: number, item: IPerson): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStaffMember>>): void {
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

  protected updateForm(staffMember: IStaffMember): void {
    this.editForm.patchValue({
      id: staffMember.id,
      defaultRole: staffMember.defaultRole,
      contractUntil: staffMember.contractUntil,
      retirementDate: staffMember.retirementDate,
      miscData: staffMember.miscData,
      person: staffMember.person,
    });

    this.peopleCollection = this.personService.addPersonToCollectionIfMissing(this.peopleCollection, staffMember.person);
  }

  protected loadRelationshipsOptions(): void {
    this.personService
      .query({ filter: 'staffmember-is-null' })
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing(people, this.editForm.get('person')!.value)))
      .subscribe((people: IPerson[]) => (this.peopleCollection = people));
  }

  protected createFromForm(): IStaffMember {
    return {
      ...new StaffMember(),
      id: this.editForm.get(['id'])!.value,
      defaultRole: this.editForm.get(['defaultRole'])!.value,
      contractUntil: this.editForm.get(['contractUntil'])!.value,
      retirementDate: this.editForm.get(['retirementDate'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      person: this.editForm.get(['person'])!.value,
    };
  }
}
