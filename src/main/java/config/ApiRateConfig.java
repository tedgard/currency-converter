package config;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "api-rates-config.properties")
public class ApiRateConfig {

	private static List<String> listCurrencies = null;
	
	@Value("${api.url.latest}")
	private String urlLatestRate;
		
	@Value("${api.url.historical}")
	private String urlHistoricalRate;

	@Value("${api.url.historical.ext}")
	private String urlHistoricalRateExt;
	
	@Value("${api.app.id}")
	private String appId;
	
	@Value("${api.app.id.field}")
	private String appIdField;

	@Value("${supported.currencies}")
	private String supportedCurrencies;


	public String getUrlLatestRate() {
		return urlLatestRate;
	}

	public void setUrlLatestRate(String urlLatestRate) {
		this.urlLatestRate = urlLatestRate;
	}

	public String getUrlHistoricalRate() {
		return urlHistoricalRate;
	}

	public void setUrlHistoricalRate(String urlHistoricalRate) {
		this.urlHistoricalRate = urlHistoricalRate;
	}

	public String getUrlHistoricalRateExt() {
		return urlHistoricalRateExt;
	}

	public void setUrlHistoricalRateExt(String urlHistoricalRateExt) {
		this.urlHistoricalRateExt = urlHistoricalRateExt;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	

	public String getAppIdField() {
		return appIdField;
	}

	public void setAppIdField(String appIdField) {
		this.appIdField = appIdField;
	}

	public void setSupportedCurrencies(String supportedCurrencies) {
		this.supportedCurrencies = supportedCurrencies;
	}
	
	public List<String> getSupportedCurrencies() {
		
		if(listCurrencies == null){
			listCurrencies = new LinkedList<String>(Arrays.asList(supportedCurrencies.replaceAll(" ", "").split(",")));
			listCurrencies = listCurrencies.stream().distinct().collect(Collectors.toList());	
			listCurrencies.sort(String::compareTo);
		}
		
		return listCurrencies;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApiRateConfig [urlLatestRate=").append(urlLatestRate).append(", urlHistoricalRate=")
				.append(urlHistoricalRate).append(", urlHistoricalRateExt=").append(urlHistoricalRateExt)
				.append(", appId=").append(appId).append(", appIdField=").append(appIdField)
				.append(", supportedCurrencies=").append(supportedCurrencies).append(", getSupportedCurrencies()=")
				.append(getSupportedCurrencies()).append("]");
		return builder.toString();
	}
	
}
