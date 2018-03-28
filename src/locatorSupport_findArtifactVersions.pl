use strict;
use warnings;
use XML::Simple;
use XML::XPath;
use Data::Dumper;
use ElectricCommander;

my $out;
my $cmdr = ElectricCommander->new();
my $dir = `mktemp -d`;
chomp $dir;

system("echo test > $dir/test.txt");

$cmdr->deleteArtifact("test:test");
$out = $cmdr->publishArtifactVersion({artifactName => "test:test",
                                version => "1.0",
                                fromDirectory => $dir
                                });
print Dumper $out->{_xml};;

$out = $cmdr->findArtifactVersions({artifactName => "test:test"});
print Dumper $out->{_xml};;

system("ec-groovy -e \"import com.electriccloud.client.groovy.ElectricFlow; println(new ElectricFlow().findArtifactVersions(artifactName: 'test:test'));\"");

# Cleanup
$cmdr->deleteArtifact("test:test");
system("rm -rf $dir");
