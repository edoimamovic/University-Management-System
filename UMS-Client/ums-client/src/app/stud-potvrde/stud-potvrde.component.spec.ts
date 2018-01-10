import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudPotvrdeComponent } from './stud-potvrde.component';

describe('StudPotvrdeComponent', () => {
  let component: StudPotvrdeComponent;
  let fixture: ComponentFixture<StudPotvrdeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudPotvrdeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudPotvrdeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
