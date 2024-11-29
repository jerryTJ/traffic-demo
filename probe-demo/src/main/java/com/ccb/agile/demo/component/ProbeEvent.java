package com.ccb.agile.demo.component;

import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProbeEvent {

  private final ApplicationAvailability availability;

  private final ApplicationEventPublisher eventPublisher;

  public ProbeEvent(ApplicationEventPublisher eventPublisher, ApplicationAvailability availability) {
    this.eventPublisher = eventPublisher;
    this.availability = availability;
  }

  public void brokenLiveness() {
    AvailabilityChangeEvent.publish(this.eventPublisher, new Exception("restart pod"), LivenessState.BROKEN);
  }

  public void stopTraffic() {
    AvailabilityChangeEvent.publish(this.eventPublisher, "stop traffic by mannual", ReadinessState.REFUSING_TRAFFIC);
  }

  public LivenessState getLivenessState() {
    return availability.getLivenessState();
  }

  public ReadinessState getReadinessState() {
    return availability.getReadinessState();

  }
}