import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudOcjeneComponent } from './stud-ocjene.component';

describe('StudOcjeneComponent', () => {
  let component: StudOcjeneComponent;
  let fixture: ComponentFixture<StudOcjeneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudOcjeneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudOcjeneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
