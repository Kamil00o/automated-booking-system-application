package pl.flywithbookedseats.passengeraccountservice.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "pl.flywithbookedseats",
        importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class DomainArchitectureTest {

    @ArchTest
    static final ArchRule domain_has_no_external_dependencies = noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideOutsideOfPackages("..domain..", "..java..", "", "..lombok..", "..slf4j..", "..jakarta..");
}
