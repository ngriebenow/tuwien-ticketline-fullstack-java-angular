package at.ac.tuwien.sepm.groupphase.backend.endpoint.contributor;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class InfoEndpointUptimeContributor implements InfoContributor {

  private final RuntimeMXBean runtimeMxBean;

  public InfoEndpointUptimeContributor() {
    runtimeMxBean = ManagementFactory.getRuntimeMXBean();
  }

  @Override
  public void contribute(Info.Builder builder) {
    builder.withDetail("uptime", Duration.of(runtimeMxBean.getUptime(), ChronoUnit.MILLIS));
  }
}
