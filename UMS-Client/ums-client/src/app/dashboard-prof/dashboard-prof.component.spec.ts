import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardProfComponent } from './dashboard-prof.component';

describe('DashboardProfComponent', () => {
  let component: DashboardProfComponent;
  let fixture: ComponentFixture<DashboardProfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardProfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardProfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
