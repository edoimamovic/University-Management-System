import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudPredmetiComponent } from './stud-predmeti.component';

describe('StudPredmetiComponent', () => {
  let component: StudPredmetiComponent;
  let fixture: ComponentFixture<StudPredmetiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudPredmetiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudPredmetiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
