import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudPredmetComponent } from './stud-predmet.component';

describe('StudPredmetComponent', () => {
  let component: StudPredmetComponent;
  let fixture: ComponentFixture<StudPredmetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudPredmetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudPredmetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
