package com.mopub.mobileads;

import com.mopub.common.test.support.SdkTestRunner;
import com.mopub.mobileads.test.support.VastUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Node;

import static com.mopub.mobileads.test.support.VastUtils.createNode;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SdkTestRunner.class)
public class VastCompanionAdConfigXmlManagerTest {

    private VastCompanionAdXmlManager subject;
    private Node companionNode;

    @Before
    public void setup() throws Exception {
        String companionXml = "<Companion id=\"valid\" height=\"250\" width=\"300\">" +
                "    <StaticResource creativeType=\"image/png\">http://pngimage</StaticResource>" +
                "    <TrackingEvents>" +
                "        <Tracking event=\"creativeView\">http://tracking/creativeView1</Tracking>" +
                "        <Tracking event=\"creativeView\">http://tracking/creativeView2</Tracking>" +
                "        <Tracking event=\"creativeView\">http://tracking/creativeView3</Tracking>" +
                "    </TrackingEvents>" +
                "    <CompanionClickThrough>http://clickthrough</CompanionClickThrough>" +
                "    <CompanionClickThrough>http://second_clickthrough</CompanionClickThrough>" +
                "    <CompanionClickTracking>" +
                "        <![CDATA[http://clicktrackingOne]]>" +
                "    </CompanionClickTracking>" +
                "    <CompanionClickTracking>" +
                "        <![CDATA[http://clicktrackingTwo]]>" +
                "    </CompanionClickTracking>" +
                "    <RandomUnusedTag>This_is_unused</RandomUnusedTag>" +
                "</Companion>";

        companionNode = createNode(companionXml);
        subject = new VastCompanionAdXmlManager(companionNode);
    }

    @Test
    public void getWidth_shouldReturnWidthAttributes() {
        assertThat(subject.getWidth()).isEqualTo(300);
    }

    @Test
    public void getWidth_withNoWidthAttribute_shouldReturnNull() throws Exception {
        String companionXml = "<Companion id=\"valid\" height=\"250\">" +
                "</Companion>";

        companionNode = createNode(companionXml);
        subject = new VastCompanionAdXmlManager(companionNode);

        assertThat(subject.getWidth()).isNull();
    }

    @Test
    public void getHeight_shouldReturnHeightAttributes() {
        assertThat(subject.getHeight()).isEqualTo(250);
    }

    @Test
    public void getHeight_withNoHeightAttribute_shouldReturnNull() throws Exception {
        String companionXml = "<Companion id=\"valid\" width=\"300\">" +
                "</Companion>";

        companionNode = createNode(companionXml);
        subject = new VastCompanionAdXmlManager(companionNode);

        assertThat(subject.getHeight()).isNull();
    }

    @Test
    public void getResourceXmlManager_shouldReturnVastResourceXmlManager() throws Exception {
        VastResourceXmlManager resourceXmlManager = subject.getResourceXmlManager();
        assertThat(resourceXmlManager.getStaticResource()).isEqualTo("http://pngimage");
        assertThat(resourceXmlManager.getStaticResourceType()).isEqualTo("image/png");
    }

    @Test
    public void getClickThroughUrl_shouldReturnFirstStringUrl() {
        assertThat(subject.getClickThroughUrl()).isEqualTo("http://clickthrough");
    }

    @Test
    public void getClickTrackers_shouldReturnAllUrls() {
        assertThat(VastUtils.vastTrackersToStrings(subject.getClickTrackers()))
                .containsOnly("http://clicktrackingOne",
                        "http://clicktrackingTwo");
    }
}
