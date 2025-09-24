param(
	[string]$Version = '0.0.17'
)

# Downloads jorbis jar into lib/ so Maven system scope can reference it.
# Usage: .\download-jorbis.ps1 ; or pass -Version '0.0.15'

$libDir = Join-Path $PSScriptRoot 'lib'
if (-not (Test-Path $libDir)) { New-Item -ItemType Directory -Path $libDir | Out-Null }

$fileName = "jorbis-$Version.jar"
$target = Join-Path $libDir $fileName

if (Test-Path $target) {
	Write-Host "Found existing $target"
	exit 0
}

$urls = @(
	"https://repo1.maven.org/maven2/com/jcraft/jorbis/$Version/jorbis-$Version.jar",
	"https://repo.maven.apache.org/maven2/com/jcraft/jorbis/$Version/jorbis-$Version.jar"
)

$success = $false
foreach ($u in $urls) {
	try {
		Write-Host "Trying $u"
		Invoke-WebRequest -Uri $u -OutFile $target -UseBasicParsing -ErrorAction Stop
		Write-Host "Downloaded $target"
		$success = $true
		break
	} catch {
		Write-Host "Failed to download from $($u): $($_.Exception.Message)"
	}
}

if (-not $success) {
	Write-Host "Could not download jorbis jar automatically. Please download jorbis-$Version.jar and place it into the lib/ folder."
	exit 1
}

# Ensure the file has the expected name used by pom.xml
if ($fileName -ne 'jorbis-0.0.17.jar') {
	# create a copy named jorbis-0.0.17.jar so pom.xml systemPath matches
	$expected = Join-Path $libDir 'jorbis-0.0.17.jar'
	if (-not (Test-Path $expected)) {
		Copy-Item -Path $target -Destination $expected
		Write-Host "Also copied to $expected"
	}
}

exit 0
