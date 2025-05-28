package at.fhv.edc.demo;

import org.eclipse.edc.runtime.metamodel.annotation.Setting;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;

public class CsvDataPlaneExtension implements ServiceExtension {
    public static final String NAME = "Dataplane CSV Extension";

    @Setting(key = "edc.csv.file.path", description = "Path for the csv file", defaultValue = "/custom/file.csv")
    public String csvFilePath;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        var monitor = context.getMonitor();

        var csvDataSourceAdapter = new CsvDataSourceAdapterImpl(csvFilePath, monitor);
        context.registerService(CsvDataSourceAdapterImpl.class, csvDataSourceAdapter);
    }
}
